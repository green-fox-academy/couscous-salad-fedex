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
- returns list of memes: meme_path, reaction_list {reaction_type, count (has to be positive number)} - all reactiontypes are listed, even if the count is 0

4. Add reaction to meme PUT '/reaction/{id}'
- required fields:
    - URI: meme id
  - body: reaction_type, count
- returns a 400 error and message if:
  - fields are missing
  - meme id is invalid
  - reaction type is invalid
- if this user already reacted to this meme with this reaction, the value of the previous input will be updated
- returns: meme_id, reaction_type
    