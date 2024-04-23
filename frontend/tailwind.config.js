/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.jsx"],
  theme: {
    extend: {
      colors: {
        'blue-light': '#9dcfe8',
        'blue-dark': '#2d6a88',
        'red-light': '#d46e51',
        'red-dark': '#9b442b',
        'white': '#efefef',
        'gray-light': 'b2afaf',
        'grady-dark': '4a4a4a',
        'black': '#171717',
        'white2': '#f8f6f6',
        
      },
      fontFamily: {
        'title': ['Martel Sans','sans-serif'],
        'paragraph': ['Montserrat', 'sans-serif']
      }
    },
  },
  plugins: [
    require('tailwindcss-animated')
  ],
}

