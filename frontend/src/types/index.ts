export interface Ingredient {
  name: string;
  grams?: number;
  preparation?: string;
}

export interface Recommendation {
  id?: string;
  title: string;
  nutritionTags: string[];
  ingredients: Array<string | Ingredient>;
  recipe: string | null;
}

export interface IdeaRequest {
  idea: string;
}

export interface RecipeRequest {
  ideaUuid: string;
}

export interface RecipeResponse {
  recipe: string;
}

export interface IdeaResponse {
  recommendations: Recommendation[];
  warnings?: string[];
  filteredCount?: number;
}
