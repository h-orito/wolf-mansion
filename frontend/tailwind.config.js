/** @type {import('tailwindcss').Config} */
module.exports = {
  mode: 'jit',
  content: ['./src/**/*.{js,ts,jsx,tsx,mdx}'],
  purge: {
    content: [
      './src/pages/**/*.{js,ts,jsx,tsx}',
      './src/components/**/*.{js,ts,jsx,tsx}'
    ],
    options: {
      safelist: {
        standard: [/^bg-/, /^text-/, /^border-/]
      }
    }
  },
  theme: {
    extend: {
      height: {
        screen: ['100vh', '100dvh']
      },
      minHeight: {
        screen: ['100vh', '100dvh']
      },
      maxHeight: {
        screen: ['100vh', '100dvh']
      }
    }
  },
  plugins: []
}
