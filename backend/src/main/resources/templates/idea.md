You are generating meal recommendations based on a single user idea.

User idea: {{idea}}

Return strictly valid JSON with the following structure:
{
  "recommendations": [
    {
      "title": string,
      "nutritionTags": [string],
      "ingredients": [string]
    }
  ]
}

Rules:
- Provide exactly 5 recommendations.
- Each recommendation must contain a title, nutritionTags array, ingredients array.
- Each nutrition tag must include a term which describes a nutritional aspect of the meal.
- Each ingredient must include the ingredient, the amount of it in a unit that makes sense for that ingredient.
- Prefer metrics units.
- Output JSON only. No markdown or code fences.
