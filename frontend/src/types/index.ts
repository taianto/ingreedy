export interface Ingredient {
  name: string;
  grams: number;
  preparation: string;
}

export interface Recommendation {
  title: string;
  nutritionTags: string[];
  ingredients: Ingredient[];
  recipe: string;
}

export interface IdeaRequest {
  idea: string;
}

export interface IdeaResponse {
  recommendations: Recommendation[];
  warnings: string[];
  filteredCount: number;
}
