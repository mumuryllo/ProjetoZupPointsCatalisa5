document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.querySelector('#loginForm');

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const usuario = document.querySelector('#usuario').value;
        const senha = document.querySelector('#senha').value;

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
            let redirected = false; // Variável para controlar o redirecionamento

            fetch('http://localhost:8080/usuarios', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            })
            .then(response => response.json())
            .then(userList => {
                console.log(userList);

                userList.forEach(user => {
                    const userRole = user.role;

                    if (!redirected) { // Verifica se já houve redirecionamento
                        if (userRole === 'ROLE_COLABORADOR') {
                            window.location.href = 'colaborador.html';
                            redirected = true; // Define como redirecionado
                        } else if (userRole === 'GESTOR') {
                            window.location.href = 'gestor.html';
                            redirected = true; // Define como redirecionado
                        }
                    }
                });
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