import { useState } from 'react';
import { IdeaForm } from './components/IdeaForm';
import { RecommendationsList } from './components/RecommendationsList';
import { ErrorMessage } from './components/ErrorMessage';
import { chatService } from './services/api';
import { IdeaResponse } from './types/index';
import './App.css';

function App() {
  const [response, setResponse] = useState<IdeaResponse | null>(null);
  const [isIdeasLoading, setIsIdeasLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [recipeLoadingById, setRecipeLoadingById] = useState<Record<string, boolean>>({});
  const [recipeErrorById, setRecipeErrorById] = useState<Record<string, string>>({});

  const handleSubmit = async (idea: string) => {
    setIsIdeasLoading(true);
    setError(null);
    setRecipeLoadingById({});
    setRecipeErrorById({});

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
      setIsIdeasLoading(false);
    }
  };

  const handleGenerateRecipe = async (ideaUuid?: string) => {
    if (!ideaUuid) {
      return;
    }

    setRecipeLoadingById((prev) => ({ ...prev, [ideaUuid]: true }));
    setRecipeErrorById((prev) => ({ ...prev, [ideaUuid]: '' }));

    try {
      const recipeResponse = await chatService.generateRecipe(ideaUuid);

      setResponse((prev) => {
        if (!prev) {
          return prev;
        }

        return {
          ...prev,
          recommendations: prev.recommendations.map((recommendation) => {
            if (recommendation.id !== ideaUuid) {
              return recommendation;
            }

            return {
              ...recommendation,
              recipe: recipeResponse.recipe,
            };
          }),
        };
      });
    } catch (err) {
      const errorMessage =
        err instanceof Error
          ? err.message
          : 'Failed to generate recipe. Please try again.';

      setRecipeErrorById((prev) => ({ ...prev, [ideaUuid]: errorMessage }));
    } finally {
      setRecipeLoadingById((prev) => ({ ...prev, [ideaUuid]: false }));
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

        <IdeaForm onSubmit={handleSubmit} isLoading={isIdeasLoading} />

        {response && (
          <RecommendationsList
            response={response}
            onGenerateRecipe={handleGenerateRecipe}
            recipeLoadingById={recipeLoadingById}
            recipeErrorById={recipeErrorById}
          />
        )}

        {!response && !isIdeasLoading && !error && (
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
