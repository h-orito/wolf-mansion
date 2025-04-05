import type { NextConfig } from 'next'
import path from 'path'

const nextConfig: NextConfig = {
  basePath: '/wolf-mansion',
  sassOptions: {
    includePaths: [path.join(__dirname, 'styles')]
  }
}

export default nextConfig
