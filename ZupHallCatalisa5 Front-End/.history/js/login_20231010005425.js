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

            fetch('http://localhost:8080/usuarios', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            })
            .then(response => response.json())
            .then(userList => {
                console.log(userList);

                for (let i = 0; i < userList.length; i++) {
                    const user = userList[i];
                    const userRole = user.role;

                    if (userRole === 'ROLE_COLABORADOR') {
                        window.location.href = 'colaborador.html';
                        return; // Sai do loop imediatamente
                    } else if (userRole === 'GESTOR') {
                        window.location.href = 'gestor.html';
                        return; // Sai do loop imediatamente
                    }
                }

                console.error('Nenhum usuário com a função adequada encontrado.');
            })
            .catch(error => {
                console.error('Erro ao obter informações do usuário:', error);
            });
        })
        .catch(error => {
            console.error('Erro na autenticação:', error);
        });
    });
});document.addEventListener('DOMContentLoaded', function () {
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

            fetch('http://localhost:8080/usuarios', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            })
            .then(response => response.json())
            .then(userList => {
                console.log(userList);

                for (let i = 0; i < userList.length; i++) {
                    const user = userList[i];
                    const userRole = user.role;

                    if (userRole === 'ROLE_COLABORADOR') {
                        window.location.href = 'colaborador.html';
                        return; // Sai do loop imediatamente
                    } else if (userRole === 'GESTOR') {
                        window.location.href = 'gestor.html';
                        return; // Sai do loop imediatamente
                    }
                }

                console.error('Nenhum usuário com a função adequada encontrado.');
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