You are generating meal recommendations based on a single user idea.

User idea: {{idea}}

Return strictly valid JSON with the following structure:
{
  "recommendations": [
    {
      "title": "string",
      "nutritionTags": ["string", "string"],
      "ingredients": [
        {
          "name": "string",
          "grams": number,
          "preparation": "string"
        }
      ],
      "recipe": "string"
    }
  ]
}

Rules:
- Provide exactly 10 recommendations.
- Each recommendation must contain a title, nutritionTags array, ingredients array, and recipe.
- Each ingredient must include grams and preparation.
- Output JSON only. No markdown or code fences.
