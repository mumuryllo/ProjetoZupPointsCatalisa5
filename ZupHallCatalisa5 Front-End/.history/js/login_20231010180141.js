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

        // Obtém os tokens e IDs já existentes (ou inicializa arrays vazios)
        const storedTokens = JSON.parse(localStorage.getItem('tokens')) || [];
        const storedIds = JSON.parse(localStorage.getItem('ids')) || [];

        // Adiciona o novo token e ID aos arrays
        storedTokens.push(data.token);
        const token = data.token;

        // Armazena o novo token e ID
        localStorage.setItem('tokens', JSON.stringify(storedTokens));

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
            storedIds.push(loggedInUser.id);

            localStorage.setItem('ids', JSON.stringify(storedIds));

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