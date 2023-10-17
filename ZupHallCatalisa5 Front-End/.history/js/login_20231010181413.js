document.addEventListener('DOMContentLoaded', function () {
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

        const token = data.token;
        localStorage.setItem(`token_${usuario}`, token); // Armazena o token com uma chave única

        fetch('http://localhost:8080/usuarios', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`
          },
        })
          .then(response => response.json())
          .then(userList => {
            console.log(userList);

            const loggedInUser = userList.find(user => user.username === usuario);
            localStorage.setItem(`id_${usuario}`, loggedInUser.id); // Armazena o ID com uma chave única

            const usuario = 'nome_do_usuario'; // Substitua pelo nome de usuário correto
const token = localStorage.getItem(`token_${usuario}`);
const id = localStorage.getItem(`id_${usuario}`);

if (token && id) {
  // Use token e id conforme necessário para autorização ou outras operações
  console.log('Token do usuário logado:', token);
  console.log('ID do usuário logado:', id);
} else {
  console.error('Token ou ID não encontrados. O usuário não está logado.');
}

            if (loggedInUser) {
              if (loggedInUser.role) {
                if (loggedInUser.role === 'ROLE_COLABORADOR') {
                  window.location.href = 'colaborador.html';
                } else if (loggedInUser.role === 'GESTOR') {
                  window.location.href = 'gestor.html';
                } else {
                  console.error('Função de usuário desconhecida:', loggedInUser.role);
                }
              } else {
                console.error('Usuário não tem uma função definida.');
              }
            } else {
              console.error('Usuário não encontrado.');
            }
          })
          .catch(error => {
            console.error('Erro ao obter informações do usuário:', error);
          });
      })
      .catch(error => {
        console.error('Erro na autenticação:', error);
      });
  });
});