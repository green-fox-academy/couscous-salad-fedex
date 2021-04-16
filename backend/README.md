Endpoints (all of them accept JSON):
1. Register POST '/register'
- accepts fields: 'username', 'email', 'password'
- returns a 4xx error if a parameter is missing or invalid with a status message
- returns 200 ok if registration is successful

2. Login POST '/login'
- accepts fields: 'email', 'password'
- returns a 4xx error if a parameter is missing or invalid with a status message
- returns 200 ok and a token if login is successful

3. Get Meme list GET '/meme'
- required fields: none
- returns list of memes: meme_id, meme_path, reaction_list [reaction_type, amount], comments 
  - all reaction_types are listed, even if the count is 0

4. GET /meme/{id}
- required fields: meme id in URI 
- returns: reaction_list [reaction_type, amount], comment
- returns the user's own reactions and comment to that meme

5. POST /meme
- send a meme to the server
- accepts an object with field "meme_path", with value of the direct URL of the meme image itself.
- returns the sent meme

6. Add reaction to meme PUT '/meme/{id}'
- required fields:
    - URI: meme id
  - body: reaction_list [reaction_type, amount]
  - optional: comment
- returns a 400 error and message if:
  - reaction_list is missing
  - meme id is invalid
  - reaction type is invalid
- if this user already reacted to this meme, the value of the previous input will be updated
- returns: same as GET /meme
