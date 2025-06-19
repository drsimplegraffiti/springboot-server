once you post your graphql endpoint in postman, you will see the following:
![img.png](img.png)

![img_1.png](img_1.png)

![img_2.png](img_2.png)

### Use graphql playground
http://localhost:9191/graphiql
![img_3.png](img_3.png)

![img_4.png](img_4.png)

### Reload with the button shown in the image below
![img_5.png](img_5.png)

![img_6.png](img_6.png)

```graphql
mutation RegisterUser {
  registerUser(
    input: {
      fullName: "lim"
      email: "lim@yopmail.com"
      role: USER
      password: "limosin"
    }
  )
}
```

```graphql
mutation Login {
    login(input: { username: "lim@yopmail.com", password: "limosin" }) {
        accessToken
        refreshToken
    }
}

```

![img_7.png](img_7.png)


```graphql
query GetProductsPaginated2 {
    getProductsPaginated(pagination: { page: 0, size: 10 }) {
        page
        size
        totalElements
        totalPages
        content {
            id
            name
            category
            stock
        }
    }
}
```