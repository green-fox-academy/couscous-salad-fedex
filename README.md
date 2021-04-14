Endpoints (all of them accept JSON):
1. Register '/register'
- accepts fields: 'username', 'email', 'password'
- returns a 4xx error if a parameter is missing or invalid with a status message
- returns 200 ok if registration is successful

2. Login '/login'
- accepts fields: 'email', 'password'
- returns a 4xx error if a parameter is missing or invalid with a status message
- returns 200 ok and a token if login is successful