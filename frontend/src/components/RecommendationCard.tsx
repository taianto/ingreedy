import React from 'react';
import { Recommendation } from '../types/index';
import './RecommendationCard.css';

interface RecommendationCardProps {
  recommendation: Recommendation;
}

export const RecommendationCard: React.FC<RecommendationCardProps> = ({
  recommendation,
}) => {
  return (
    <div className="recommendation-card">
      <h3 className="recommendation-title">{recommendation.title}</h3>
      
      <div className="nutrition-tags">
        {recommendation.nutritionTags.map((tag, index) => (
          <span key={index} className="tag">
            {tag}
          </span>
        ))}
      </div>

      <div className="ingredients-section">
        <h4>Ingredients</h4>
        <ul className="ingredients-list">
          {recommendation.ingredients.map((ingredient, index) => (
            <li key={index}>
              <strong>{ingredient.name}</strong> - {ingredient.grams}g
              <br />
              <span className="preparation">{ingredient.preparation}</span>
            </li>
          ))}
        </ul>
      </div>

      <div className="recipe-section">
        <h4>Recipe</h4>
        <p className="recipe-text">{recommendation.recipe}</p>
      </div>
    </div>
  );
};
