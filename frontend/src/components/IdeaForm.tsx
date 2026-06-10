import React, { useState } from 'react';
import './IdeaForm.css';

interface IdeaFormProps {
  onSubmit: (idea: string) => void;
  isLoading: boolean;
}

export const IdeaForm: React.FC<IdeaFormProps> = ({ onSubmit, isLoading }) => {
  const [idea, setIdea] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (idea.trim()) {
      onSubmit(idea);
      setIdea('');
    }
  };

  return (
    <form className="idea-form" onSubmit={handleSubmit}>
      <div className="form-group">
        <label htmlFor="idea">What's your meal idea?</label>
        <textarea
          id="idea"
          value={idea}
          onChange={(e) => setIdea(e.target.value)}
          placeholder="e.g., I have chicken, tomatoes, and garlic..."
          disabled={isLoading}
          rows={4}
        />
      </div>
      <button type="submit" disabled={!idea.trim() || isLoading}>
        {isLoading ? 'Generating...' : 'Get Recommendations'}
      </button>
    </form>
  );
};
