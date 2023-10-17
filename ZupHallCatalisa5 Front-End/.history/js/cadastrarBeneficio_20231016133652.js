document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('id');
  
    const cadastrarBeneficioButton = document.getElementById('cadastrarBeneficioButton');
    const nomeInput = document.getElementById('floatingInputNome');
    const pontosInput = document.getElementById('floatingInputPontos');
    const disponivelInput = document.getElementById('floatingInputDisponivel');
    const valorInput = document.getElementById('floatingInputValor');
  
    cadastrarBeneficioButton.addEventListener('click', function () {
      const nome = nomeInput.value;
      const qtdPontosParaComprar = parseInt(pontosInput.value);
      const qtdDisponivel = parseInt(disponivelInput.value);
      const valor = parseFloat(valorInput.value);
  
      const beneficioData = {
        nome: nome,
        qtdPontosParaComprar: qtdPontosParaComprar,
        qtdDisponivel: qtdDisponivel,
        valor: valor
      };
  
      fetch(`http://localhost:8080/beneficios`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(beneficioData)
      })
        .then(response => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Falha na requisição');
          }
        })
        .then(beneficio => {
          alert('Benefício cadastrado com sucesso:', beneficio);
          window.location.href = 'colaborador.html';
        })
        .catch(error => {
          console.error('Ocorreu um erro ao cadastrar o benefício:', error);
        });
    });
  });