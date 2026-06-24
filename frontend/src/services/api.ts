import axios from 'axios';
import {
  IdeaRequest,
  IdeaResponse,
  RecipeRequest,
  RecipeResponse,
} from '../types/index';

const API_URL = import.meta.env.VITE_API_URL || '/api';

const apiClient = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const chatService = {
  generateIdea: async (idea: string): Promise<IdeaResponse> => {
    const request: IdeaRequest = { idea };
    const response = await apiClient.post<IdeaResponse>('/chat/idea', request);
    return response.data;
  },

  generateRecipe: async (ideaUuid: string): Promise<RecipeResponse> => {
    const request: RecipeRequest = { ideaUuid };
    const response = await apiClient.post<RecipeResponse>('/chat/recipe', request);
    return response.data;
  },
};

export default apiClient;
