import React from 'react';
import { Recommendation } from '../types/index';
import './RecommendationCard.css';

interface RecommendationCardProps {
  recommendation: Recommendation;
  onGenerateRecipe: (ideaUuid?: string) => void;
  isRecipeLoading: boolean;
  recipeError?: string;
}

export const RecommendationCard: React.FC<RecommendationCardProps> = ({
  recommendation,
  onGenerateRecipe,
  isRecipeLoading,
  recipeError,
}) => {
  const ingredients = Array.isArray(recommendation.ingredients) ? recommendation.ingredients : [];
  const hasRecipe = Boolean(recommendation.recipe);
  const canGenerateRecipe = Boolean(recommendation.id) && !hasRecipe;

  return (
    <div className="recommendation-card">
      <h3 className="recommendation-title">{recommendation.title}</h3>

      <div className="nutrition-tags">
        {(recommendation.nutritionTags ?? []).map((tag, index) => (
          <span key={index} className="tag">
            {tag}
          </span>
        ))}
      </div>

      <div className="ingredients-section">
        <h4>Ingredients</h4>
        <ul className="ingredients-list">
          {ingredients.map((ingredient, index) => {
            if (typeof ingredient === 'string') {
              return <li key={index}>{ingredient}</li>;
            }

            return (
              <li key={index}>
                <strong>{ingredient.name}</strong>
                {typeof ingredient.grams === 'number' && <> - {ingredient.grams}g</>}
                {ingredient.preparation && (
                  <>
                    <br />
                    <span className="preparation">{ingredient.preparation}</span>
                  </>
                )}
              </li>
            );
          })}
        </ul>
      </div>

      <div className="recipe-section">
        <h4>Recipe</h4>

        {canGenerateRecipe && (
          <button
            type="button"
            className="generate-recipe-button"
            disabled={isRecipeLoading}
            onClick={() => onGenerateRecipe(recommendation.id)}
          >
            {isRecipeLoading ? (
              <>
                <span className="spinner" aria-hidden="true" />
                Generating recipe...
              </>
            ) : (
              'Generate Recipe'
            )}
          </button>
        )}

        {recipeError && <p className="recipe-error">{recipeError}</p>}

        {hasRecipe && <p className="recipe-text">{recommendation.recipe}</p>}

        {!hasRecipe && !isRecipeLoading && !recipeError && (
          <p className="recipe-placeholder">Generate a full recipe for this idea.</p>
        )}
      </div>
    </div>
  );
};
