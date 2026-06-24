import React from 'react';
import { IdeaResponse } from '../types/index';
import { RecommendationCard } from './RecommendationCard';
import './RecommendationsList.css';

interface RecommendationsListProps {
  response: IdeaResponse;
  onGenerateRecipe: (ideaUuid?: string) => void;
  recipeLoadingById: Record<string, boolean>;
  recipeErrorById: Record<string, string>;
}

export const RecommendationsList: React.FC<RecommendationsListProps> = ({
  response,
  onGenerateRecipe,
  recipeLoadingById,
  recipeErrorById,
}) => {
  const recommendations = Array.isArray(response?.recommendations)
    ? response.recommendations
    : [];
  const warnings = Array.isArray(response?.warnings) ? response.warnings : [];
  const filteredCount = typeof response?.filteredCount === 'number' ? response.filteredCount : 0;

  return (
    <div className="recommendations-container">
      {warnings.length > 0 && (
        <div className="warnings-section">
          <h3>Warnings</h3>
          <ul className="warnings-list">
            {warnings.map((warning, index) => (
              <li key={index}>{warning}</li>
            ))}
          </ul>
        </div>
      )}

      <div className="recommendations-stats">
        <p>
          Found <strong>{recommendations.length}</strong> recommendations
          {filteredCount > 0 && <> ({filteredCount} filtered out)</>}
        </p>
      </div>

      <div className="recommendations-grid">
        {recommendations.map((recommendation, index) => (
          <RecommendationCard
            key={recommendation.id ?? index}
            recommendation={recommendation}
            onGenerateRecipe={onGenerateRecipe}
            isRecipeLoading={Boolean(recommendation.id && recipeLoadingById[recommendation.id])}
            recipeError={recommendation.id ? recipeErrorById[recommendation.id] : ''}
          />
        ))}
      </div>

      {recommendations.length === 0 && (
        <div className="no-recommendations">
          <p>No recommendations found. Try a different meal idea!</p>
        </div>
      )}
    </div>
  );
};
