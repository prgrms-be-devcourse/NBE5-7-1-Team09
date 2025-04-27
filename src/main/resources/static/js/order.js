function enableEditMode(btn) {
    const form = btn.closest('form');
    form.querySelectorAll('.view-field').forEach(el => el.style.display = 'none');
    form.querySelectorAll('.edit-field').forEach(el => el.style.display = 'inline-block');
    btn.style.display = 'none';
    form.querySelector('.save').style.display = 'inline-block';
}

function cancelOrder(orderId) {
    if (!confirm('정말 주문을 취소하시겠습니까?')) return;

    fetch(`/orders/${orderId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: '_method=DELETE'
    })
        .then(response => {
            if (response.ok) {
                alert('주문이 취소되었습니다.');
                location.reload();
            } else {
                alert('취소 실패');
            }
        });
}
