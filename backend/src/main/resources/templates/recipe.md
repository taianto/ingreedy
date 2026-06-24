You are generating a recipe based on the following ingredients: {{ingredients}}

Return strictly valid JSON with the following structure:
{
  "recipe": string
}

- The steps must be numbered and in order
- THE FINAL DISH MUST BE EDIBLE AND HEALTHY TO CONSUME!!
- The final dish must include all the ingredients provided
- The recipe must be extremely detailed, clear and concise
  - Output JSON only. Start each step in the recipe on a new line for better readability.