import React from 'react';
import './ErrorMessage.css';

interface ErrorMessageProps {
  message: string;
  onClose: () => void;
}

export const ErrorMessage: React.FC<ErrorMessageProps> = ({ message, onClose }) => {
  return (
    <div className="error-message">
      <div className="error-content">
        <span className="error-icon">⚠️</span>
        <p>{message}</p>
        <button onClick={onClose} className="close-btn">×</button>
      </div>
    </div>
  );
};
