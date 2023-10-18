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
  
      if (!nome.trim()) {
        document.getElementById('nomeError').textContent = 'Nome não pode ser nulo';
      }
  
      if (!email.trim()) {
        document.getElementById('emailError').textContent = 'Email não pode ser nulo';
      } else if (!isValidEmail(email)) {
        document.getElementById('emailError').textContent = 'Email inválido';
      }
  
  
      if (!senha.trim()) {
        document.getElementById('passwordError').textContent = 'Senha não pode ser nula';
      } else if (senha.length < 6) {
        document.getElementById('passwordError').textContent = 'Senha deve ter pelo menos 6 caracteres';
      }
  
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
            alert('Cadastro bem-sucedido:', data);
            window.location.href = 'index.html';
          })
          .catch(error => {
            console.error('Erro ao cadastrar usuário:', error);
=          });
      }
    });
  
    // Função para validar o formato do email
    function isValidEmail(email) {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return emailRegex.test(email);
    }
  });