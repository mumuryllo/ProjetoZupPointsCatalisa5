document.addEventListener('DOMContentLoaded', function () {
  const token = localStorage.getItem('token');
  const userId = localStorage.getItem('id');
  const certificadosAccordion = document.querySelector('#certificadosAccordion');

  console.log('UserID:', userId);

  fetch(`http://localhost:8080/certificados?colaborador_id=${userId}`, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  })
    .then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Falha na requisição');
      }
    })
    .then(certificados => {
      console.log('Certificados:', certificados);
      
      certificadosAccordion.innerHTML = ''; // Limpa o conteúdo existente
      certificados.forEach(certificado => {
        const certificadoItem = document.createElement('div');
        certificadoItem.className = 'accordion-item';

        if(certificado.colaborador==userId){

        }

      });
    })
    .catch(error => {
      console.error('Ocorreu um erro:', error);
    });
});