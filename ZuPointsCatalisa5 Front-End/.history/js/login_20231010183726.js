document.addEventListener('DOMContentLoaded', function () {
  const token = localStorage.getItem('token'); // Verifica se já existe um token no localStorage

  if (token) {
    // Se o token já está no localStorage, você pode usá-lo para fazer as requisições diretamente
    makeAuthenticatedRequest(token);
  } else {
    // Caso contrário, siga o processo normal de autenticação para obter um novo token
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
          console.log('Token de autenticação:', data.token);
          localStorage.setItem('token', data.token);

          // Use o token recém-obtido para fazer a requisição
          makeAuthenticatedRequest(data.token);
        })
        .catch(error => {
          console.error('Erro na autenticação:', error);
        });
    });
  }
});

function makeAuthenticatedRequest(token) {
  // Use o token para fazer a requisição de usuário logado ou qualquer outra requisição autenticada
  fetch('http://localhost:8080/usuarios', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  })
    .then(response => response.json())
    .then(userList => {
      // Faça o que precisa com os dados do usuário
      console.log(userList);
    })
    .catch(error => {
      console.error('Erro ao obter informações do usuário:', error);
    });
}