# Ingreedy

Ingreedy is an AI-powered recipe recommendation app. Enter a meal idea or a list of ingredients, and the app generates recipe suggestions with nutrition tags, ingredient quantities, preparation notes, and cooking instructions.

## Tech Stack

* React 18
* TypeScript
* Vite
* Spring Boot
* Java 21
* OpenAI API
* Axios
* Caffeine caching

## Getting Started

### Prerequisites

* Java 21
* Node.js 16 or later
* npm
* An OpenAI API key

### Clone the Repository

```bash
git clone https://github.com/taianto/ingreedy.git
cd ingreedy
```

### Start the Backend

Set your OpenAI API key:

```bash
export OPENAI_API_KEY="your-api-key"
```

On Windows PowerShell:

```powershell
$env:OPENAI_API_KEY="your-api-key"
```

Run the Spring Boot application:

```bash
cd backend
./mvnw spring-boot:run
```

The backend runs at:

```text
http://localhost:8080
```

### Start the Frontend

Open another terminal:

```bash
cd frontend
cp .env.example .env
npm install
npm run dev
```

The frontend runs at:

```text
http://localhost:3000
```

## API

The frontend sends meal ideas to:

```http
POST /chat/idea
```

Example request:

```json
{
  "idea": "A quick vegetarian pasta with mushrooms"
}
```

## Project Structure

```text
ingreedy/
├── backend/     # Spring Boot API and OpenAI integration
└── frontend/    # React and TypeScript web application
```

## Production Build

```bash
cd frontend
npm run build
```

The production files are generated in `frontend/dist`.
