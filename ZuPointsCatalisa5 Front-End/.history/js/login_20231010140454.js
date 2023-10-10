document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.querySelector('#loginForm');
  
    loginForm.addEventListener('submit', function (e) {
      e.preventDefault();
  
      const usuario = document.querySelector('[name="floatingInputUser"]').value;
      const senha = document.querySelector('[name="floatingInputPassword"]').value;
  
      // Limpa mensagens de erro anteriores
      document.getElementById('emailError').textContent = '';
      document.getElementById('passwordError').textContent = '';
  
      // Validação do email
      if (!usuario.trim()) {
        document.getElementById('emailError').textContent = 'Email não pode ser nulo';
      } else if (!isValidEmail(usuario)) {
        document.getElementById('emailError').textContent = 'Email inválido';
      }
  
      // Validação da senha
      if (!senha.trim()) {
        document.getElementById('passwordError').textContent = 'Senha não pode ser nula';
      } else if (senha.length < 6) {
        document.getElementById('passwordError').textContent = 'Senha deve ter pelo menos 6 caracteres';
      }
  
      // Se não houver erros de validação, envie a solicitação
      if (!document.getElementById('emailError').textContent && !document.getElementById('passwordError').textContent) {
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
  
            const token = localStorage.getItem('token');
  
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
  
                if (loggedInUser) {
                  const userRole = loggedInUser.role;
  
                  if (userRole === 'ROLE_COLABORADOR' || userRole === 'ROLE_GESTOR') {
                    console.error('Usuário tem múltiplas funções. Especifique uma função.');
                  } else if (userRole === 'ROLE_COLABORADOR') {
                    window.location.href = 'colaborador.html';
                  } else if (userRole === 'GESTOR') {
                    window.location.href = 'gestor.html';
                  } else {
                    console.error('Função de usuário desconhecida:', userRole);
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
      }
    });
  
    // Função para validar o formato do email
    function isValidEmail(email) {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return emailRegex.test(email);
    }
  });