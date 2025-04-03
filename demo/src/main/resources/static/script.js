/*document.getElementById('feedbackForm').addEventListener('submit', function(event) {
    event.preventDefault();
    fetch('http://localhost:8080/api/givefeedback', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            name: document.getElementById('name').value,
            registerNumber: document.getElementById('registerNumber').value,
            department: document.getElementById('department').value,
            feedbackText: document.getElementById('feedbackText').value
        })
    }).then(response => response.text()).then(alert); // Keep alert for POST response
});

function fetchResults() {
    fetch('http://localhost:8080/api/showresult')
        .then(response => response.json())
        .then(data => {
            const resultDiv = document.getElementById('result');
            resultDiv.innerHTML = ''; // Clear previous results

            if (data && Array.isArray(data) && data.length > 0) {
                const ul = document.createElement('ul');
                data.forEach(item => {
                    const li = document.createElement('li');
                    li.textContent = JSON.stringify(item); // Display each item as a string
                    ul.appendChild(li);
                });
                resultDiv.appendChild(ul);
            } else {
                resultDiv.textContent = 'No results found.';
            }
        });
}*/


document.getElementById('feedbackForm').addEventListener('submit', function(event) {
    event.preventDefault();
    fetch('http://localhost:8080/api/givefeedback', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            name: document.getElementById('name').value,
            registerNumber: document.getElementById('registerNumber').value,
            department: document.getElementById('department').value,
            feedbackText: document.getElementById('feedbackText').value
        })
    }).then(response => response.text()).then(alert);
});

function fetchResults() {
    fetch('http://localhost:8080/api/showresult')
        .then(response => response.json())
		.then(data => {
		    alert(data); // Display the data in an alert box
		  })
		  .catch(error => {
		    console.error('Error:', error);
		  });
}