document.addEventListener('DOMContentLoaded', function () {
    const cadastrarButton = document.querySelector('#cadastrarButton');
  
    cadastrarButton.addEventListener('click', function () {
      const nome = document.querySelector('[name="floatingInputNome"]').value;
      const email = document.querySelector('[name="floatingInputEmail"]').value;
      const senha = document.querySelector('[name="floatingInputSenha"]').value;
  
      // Limpa mensagens de erro anteriores
      document.getElementById('nomeError').textContent = '';
      document.getElementById('emailError').textContent = '';
      document.getElementById('passwordError').textContent = '';
  
      // Validação do nome
      if (!nome.trim()) {
        document.getElementById('nomeError').textContent = 'Nome não pode ser nulo';
      }
  
      // Validação do email
      if (!email.trim()) {
        document.getElementById('emailError').textContent = 'Email não pode ser nulo';
      } else if (!isValidEmail(email)) {
        document.getElementById('emailError').textContent = 'Email inválido';
      }
  
      // Validação da senha
      if (!senha.trim()) {
        document.getElementById('passwordError').textContent = 'Senha não pode ser nula';
      } else if (senha.length < 6) {
        document.getElementById('passwordError').textContent = 'Senha deve ter pelo menos 6 caracteres';
      }
  
      // Se não houver erros de validação, envie a solicitação de cadastro
      if (!document.getElementById('nomeError').textContent && !document.getElementById('emailError').textContent && !document.getElementById('passwordError').textContent) {
        const userData = {
          nome: nome,
          email: email,
          senha: senha,
        };
  
        fetch('http://localhost:8080/usuarios', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(userData),
        })
          .then(response => response.json())
          .then(data => {
            // Lidar com a resposta do servidor (por exemplo, exibir uma mensagem de sucesso)
            console.log('Cadastro bem-sucedido:', data);
            // Redirecionar para a página de login após o cadastro
            window.location.href = 'index.html';
          })
          .catch(error => {
            console.error('Erro ao cadastrar usuário:', error);
            // Lidar com erros (por exemplo, exibir uma mensagem de erro)
          });
      }
    });
  
    // Função para validar o formato do email
    function isValidEmail(email) {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return emailRegex.test(email);
    }
  });