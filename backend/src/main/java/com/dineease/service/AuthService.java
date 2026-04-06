package com.dineease.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dineease.config.JwtProperties;
import com.dineease.dto.AuthResponse;
import com.dineease.dto.LoginRequest;
import com.dineease.dto.RegisterRequest;
import com.dineease.dto.UserResponse;
import com.dineease.entity.Role;
import com.dineease.entity.User;
import com.dineease.exception.DuplicateResourceException;
import com.dineease.exception.ResourceNotFoundException;
import com.dineease.repository.UserRepository;
import com.dineease.security.JwtService;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;
    private final JwtProperties jwtProperties;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
            AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
            UserMapper userMapper, JwtProperties jwtProperties) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
        this.jwtProperties = jwtProperties;
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email đã được sử dụng");
        }
        if (userRepository.existsByPhone(request.phone())) {
            throw new DuplicateResourceException("Số điện thoại đã được sử dụng");
        }

        User user = User.builder()
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .fullName(request.fullName())
            .phone(request.phone())
            .role(Role.CUSTOMER) 
            .status("ACTIVE")
            .build();

        user = userRepository.save(user);
        log.info("Khách hàng mới đã đăng ký: {}", user.getEmail());
        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        // 1. Xác thực bằng AuthenticationManager (Sẽ ném lỗi 401 nếu sai mật khẩu/email)
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // 2. Lấy thông tin User từ DB
        User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new ResourceNotFoundException("User không tồn tại"));

        if ("BANNED".equals(user.getStatus())) {
            throw new org.springframework.security.authentication.LockedException("Tài khoản đã bị khóa");
        }

        // 3. Load UserDetails để lấy Authorities (Roles) và Sinh Token
        var userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        log.info("User đăng nhập thành công: {}", user.getEmail());

        return AuthResponse.of(accessToken, refreshToken, jwtProperties.getAccessTokenExpirationMs(), userMapper.toResponse(user));
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User", email));
        return userMapper.toResponse(user);
    }
}