document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('id');
  
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
        tipoCertificado: tipoCertificado
      };
  
      fetch(`http://localhost:8080/certificados?colaborador_id=${userId}`, {
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
          window.location.href=''
        })
        .catch(error => {
          console.error('Ocorreu um erro ao cadastrar o certificado:', error);
        });
    });
  });