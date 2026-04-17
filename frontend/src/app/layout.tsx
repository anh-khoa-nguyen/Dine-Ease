import type { Metadata } from 'next';
import { Inter } from 'next/font/google';
import './globals.css';
import QueryProvider from '@/providers/QueryProvider';

const inter = Inter({ subsets: ['latin', 'vietnamese'] });

export const metadata: Metadata = {
  title: 'Dine-Ease | Đặt bàn nhà hàng',
  description: 'Hệ thống đặt bàn nhà hàng tiện lợi',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="vi">
      <body className={inter.className}>
        {/* Bọc toàn bộ App bằng React Query để các trang đều dùng được */}
        <QueryProvider>
          {children}
        </QueryProvider>
      </body>
    </html>
  );
}