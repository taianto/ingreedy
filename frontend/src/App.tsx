import React, { useState } from 'react';
import { IdeaForm } from './components/IdeaForm';
import { RecommendationsList } from './components/RecommendationsList';
import { ErrorMessage } from './components/ErrorMessage';
import { chatService } from './services/api';
import { IdeaResponse } from './types/index';
import './App.css';

function App() {
  const [response, setResponse] = useState<IdeaResponse | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (idea: string) => {
    setIsLoading(true);
    setError(null);
    try {
      const result = await chatService.generateIdea(idea);
      setResponse(result);
    } catch (err) {
      const errorMessage = 
        err instanceof Error 
          ? err.message 
          : 'Failed to generate recommendations. Please try again.';
      setError(errorMessage);
      setResponse(null);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="app">
      <header className="app-header">
        <h1>🍽️ Ingreedy</h1>
        <p>Get delicious recipe recommendations based on your meal ideas</p>
      </header>

      <main className="app-main">
        {error && (
          <ErrorMessage 
            message={error} 
            onClose={() => setError(null)} 
          />
        )}

        <IdeaForm onSubmit={handleSubmit} isLoading={isLoading} />

        {response && <RecommendationsList response={response} />}

        {!response && !isLoading && !error && (
          <div className="welcome-message">
            <p>👇 Enter your meal idea to get started!</p>
          </div>
        )}
      </main>

      <footer className="app-footer">
        <p>Powered by AI-powered recipe recommendations</p>
      </footer>
    </div>
  );
}

export default App;
