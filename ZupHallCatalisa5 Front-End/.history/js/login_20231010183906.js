document.addEventListener('DOMContentLoaded', function () {
  const token = localStorage.getItem('token');

  if (token) {
    // Token já existe no localStorage, significa que o usuário está logado.
    // Você pode usar esse token para fazer requisições autenticadas diretamente.
    makeAuthenticatedRequest(token);
  } else {
    const loginForm = document.querySelector('#loginForm');

    loginForm.addEventListener('submit', function (e) {
      e.preventDefault();

      const usuario = document.querySelector('[name="floatingInputUser"]').value;
      const senha = document.querySelector('[name="floatingInputPassword"]').value;

      const loginData = {
        username: usuario,
        password: senha,
      };

      fetch('http://localhost:8080/login/auth', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(loginData),
      })
        .then(response => response.json())
        .then(data => {
          if (data.token) {
            // Token de autenticação válido obtido com sucesso.
            localStorage.setItem('token', data.token);
            makeAuthenticatedRequest(data.token);
          } else {
            console.error('Falha na autenticação: Token não obtido.');
          }
        })
        .catch(error => {
          console.error('Erro na autenticação:', error);
        });
    });
  }
});

function makeAuthenticatedRequest(token) {
  // Use o token para fazer a requisição de usuário logado ou qualquer outra requisição autenticada.
  fetch('http://localhost:8080/usuarios', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  })
    .then(response => response.json())
    .then(userList => {
      console.log(userList);
      // Faça o que precisa com os dados do usuário
    })
    .catch(error => {
      console.error('Erro ao obter informações do usuário:', error);
    });
}