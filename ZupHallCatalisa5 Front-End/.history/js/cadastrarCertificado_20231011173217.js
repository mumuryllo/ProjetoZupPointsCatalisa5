document.addEventListener('DOMContentLoaded', function () {
  const userId = localStorage.getItem('id');
  const token = localStorage.getItem(`token_${userId}`); // Use o token do usuário logado

  const cadastrarButton = document.getElementById('cadastrarButton');
  const linkInput = document.getElementById('floatingInputLink');
  const nomeInput = document.getElementById('floatingInputNome');
  const credencialInput = document.getElementById('floatingInputCredencial');
  const tipoCertificadoSelect = document.getElementById('tipoCertificado');

  cadastrarButton.addEventListener('click', function () {
    const link = linkInput.value;
    const nome = nomeInput.value;
    const numeroCredencial = credencialInput.value;
    const tipoCertificado = tipoCertificadoSelect.value;

    const certificadoData = {
      link: link,
      nome: nome,
      numero_credencial: numeroCredencial,
      tipoCertificado: tipoCertificado,
      colaborador_id: userId // Associe o certificado ao usuário logado
    };

    fetch('http://localhost:8080/certificados', { // Use o endpoint correto para cadastro de certificado
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(certificadoData)
    })
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error('Falha na requisição');
        }
      })
      .then(certificado => {
        alert('Certificado cadastrado com sucesso:', certificado);
        window.location.href = 'colaborador.html';
      })
      .catch(error => {
        console.error('Ocorreu um erro ao cadastrar o certificado:', error);
      });
  });
});