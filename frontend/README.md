# Ingreedy Frontend

A modern React application for getting AI-powered recipe recommendations based on your meal ideas.

## Prerequisites

- Node.js 16+ and npm (or yarn/pnpm)
- Backend API running on `http://localhost:8080`

## Getting Started

### 1. Install Dependencies

```bash
npm install
```

### 2. Configure Environment

Create a `.env` file in the frontend directory:

```bash
cp .env.example .env
```

Edit `.env` if your backend is running on a different URL:

```
VITE_API_URL=http://localhost:8080
```

### 3. Start Development Server

```bash
npm run dev
```

The application will be available at `http://localhost:3000`

### 4. Build for Production

```bash
npm run build
```

The built files will be in the `dist` directory.

## Features

- **Meal Idea Input**: Enter any meal idea or ingredients you have
- **AI-Generated Recommendations**: Get recipe recommendations powered by the backend AI
- **Rich Recipe Cards**: View detailed recipes with:
  - Recipe title
  - Nutrition tags
  - Ingredient lists with preparation instructions
  - Step-by-step cooking instructions
- **Error Handling**: Clear error messages when something goes wrong
- **Responsive Design**: Works great on desktop and mobile devices

## Project Structure

```
src/
├── components/        # Reusable React components
│   ├── IdeaForm.tsx   # Input form for meal ideas
│   ├── RecommendationCard.tsx  # Individual recipe card
│   ├── RecommendationsList.tsx # Container for all recipes
│   └── ErrorMessage.tsx         # Error notification
├── services/          # API communication
│   └── api.ts        # Axios client and API methods
├── types/            # TypeScript type definitions
│   └── index.ts
├── App.tsx           # Main application component
├── App.css           # App styles
├── main.tsx          # React entry point
└── index.css         # Global styles
```

## API Integration

The frontend communicates with the backend at:

- **Endpoint**: `POST /chat/idea`
- **Request**: `{ idea: string }`
- **Response**: 
  ```json
  {
    "recommendations": [
      {
        "title": "Recipe Title",
        "nutritionTags": ["tag1", "tag2"],
        "ingredients": [
          {
            "name": "ingredient",
            "grams": 100,
            "preparation": "chopped"
          }
        ],
        "recipe": "instructions..."
      }
    ],
    "warnings": ["warning1"],
    "filteredCount": 0
  }
  ```

## Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build

## Technologies Used

- **React 18**: UI library
- **TypeScript**: Type safety
- **Vite**: Fast build tool and dev server
- **Axios**: HTTP client for API communication
- **CSS**: Styling (no external CSS framework dependencies)

## Troubleshooting

### Backend Connection Issues

If you see connection errors:

1. Ensure the Spring Boot backend is running on `http://localhost:8080`
2. Check the `VITE_API_URL` in `.env` matches your backend URL
3. Verify CORS is enabled on your backend (if backend and frontend run on different domains)

### Development Server Issues

If the dev server won't start:

```bash
# Clear node_modules and reinstall
rm -rf node_modules package-lock.json
npm install
npm run dev
```

## License

MIT
