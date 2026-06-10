import React from 'react';
import { IdeaResponse } from '../types/index';
import { RecommendationCard } from './RecommendationCard';
import './RecommendationsList.css';

interface RecommendationsListProps {
  response: IdeaResponse;
}

export const RecommendationsList: React.FC<RecommendationsListProps> = ({
  response,
}) => {
  return (
    <div className="recommendations-container">
      {response.warnings.length > 0 && (
        <div className="warnings-section">
          <h3>Warnings</h3>
          <ul className="warnings-list">
            {response.warnings.map((warning, index) => (
              <li key={index}>{warning}</li>
            ))}
          </ul>
        </div>
      )}

      <div className="recommendations-stats">
        <p>
          Found <strong>{response.recommendations.length}</strong> recommendations
          {response.filteredCount > 0 && (
            <> ({response.filteredCount} filtered out)</>
          )}
        </p>
      </div>

      <div className="recommendations-grid">
        {response.recommendations.map((recommendation, index) => (
          <RecommendationCard key={index} recommendation={recommendation} />
        ))}
      </div>

      {response.recommendations.length === 0 && (
        <div className="no-recommendations">
          <p>No recommendations found. Try a different meal idea!</p>
        </div>
      )}
    </div>
  );
};
