import type { Config } from 'tailwindcss'

export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        // --- Lightest creams ---
        cream: {
          DEFAULT: '#fcf9f5',
          warm: '#faf5ef',
        },
        ivory: {
          DEFAULT: '#f8f2e9',
          warm: '#f6eee2',
        },
        // --- Light tans ---
        linen: {
          DEFAULT: '#f4eadc',
          warm: '#f2e7d6',
        },
        parchment: {
          DEFAULT: '#f0e3d0',
          warm: '#eedfc9',
        },
        // --- Sands ---
        sand: {
          light: '#ecdbc3',
          DEFAULT: '#eedec7',
          warm: '#ebd8bd',
          dark: '#e9d4b7',
        },
        // --- Warm midtones ---
        wheat: {
          light: '#e7d0b0',
          DEFAULT: '#e5cdaa',
          warm: '#e3c9a4',
          dark: '#e1c59d',
        },
        honey: {
          light: '#dfc297',
        },
        // --- Caramels ---
        caramel: {
          light: '#ddbe91',
          DEFAULT: '#d6af78',
          warm: '#d4ac72',
          dark: '#d0a465',
        },
        // --- Golds ---
        gold: {
          light: '#cea05f',
          DEFAULT: '#c8954c',
          warm: '#c58e40',
          dark: '#c28a3a',
        },
        // --- Ambers ---
        amber: {
          light: '#bb8639',
          DEFAULT: '#a97833',
          warm: '#a27431',
          dark: '#966b2d',
        },
        // --- Browns ---
        bronze: {
          light: '#8f662b',
          DEFAULT: '#835d27',
          warm: '#765424',
          dark: '#6a4c20',
        },
        // --- Dark tones ---
        walnut: {
          light: '#64471e',
          DEFAULT: '#5d431c',
          warm: '#513a18',
          dark: '#443114',
        },
        // --- Deepest / near-black ---
        coffee: {
          light: '#3e2c13',
          DEFAULT: '#382811',
          warm: '#2b1f0d',
          dark: '#251a0b',
        },
        espresso: {
          DEFAULT: '#1f1609',
          warm: '#191107',
          dark: '#120d05',
        },
        dark: '#0c0803',
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', '-apple-system', 'sans-serif'],
      },
      boxShadow: {
        sm: '0 1px 2px 0 rgba(200, 149, 76, 0.06)',
        md: '0 4px 6px -1px rgba(200, 149, 76, 0.08), 0 2px 4px -2px rgba(200, 149, 76, 0.06)',
        lg: '0 10px 15px -3px rgba(200, 149, 76, 0.1), 0 4px 6px -4px rgba(200, 149, 76, 0.06)',
        xl: '0 20px 25px -5px rgba(200, 149, 76, 0.15), 0 8px 10px -6px rgba(200, 149, 76, 0.08)',
      },
      borderRadius: {
        xl: '1rem',
        '2xl': '1.25rem',
      }
    },
  },
  plugins: [],
} satisfies Config
