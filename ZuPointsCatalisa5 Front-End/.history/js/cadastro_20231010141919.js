document.addEventListener('DOMContentLoaded', function () {
    const cadastrarButton = document.querySelector('#cadastrarButton');
  
    cadastrarButton.addEventListener('click', function () {
      const nome = document.querySelector('[name="floatingInputNome"]').value;
      const email = document.querySelector('[name="floatingInputEmail"]').value;
      const senha = document.querySelector('[name="floatingInputSenha"]').value;
  
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
          c('Cadastro bem-sucedido:', data);
          window.location.href = 'index.html';
        })
        .catch(error => {
          console.error('Erro ao cadastrar usuário:', error);
        });
    });
  });