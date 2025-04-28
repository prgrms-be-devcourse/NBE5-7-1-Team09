function enableEditMode(btn) {
    const form = btn.closest('form');
    const status = form.querySelector('.status').innerText.trim();

    if (status === '배송중') {
        alert('배송중인 주문은 수정할 수 없습니다.');
        return;
    }

    form.querySelectorAll('.view-field').forEach(el => el.style.display = 'none');
    form.querySelectorAll('.edit-field').forEach(el => el.style.display = 'inline-block');
    btn.style.display = 'none';
    form.querySelector('.save').style.display = 'inline-block';
}

function cancelOrder(orderId) {
    const form = document.querySelector(`form[data-order-id="${orderId}"]`);
    const status = form.querySelector('.status').innerText.trim();

    if (status === '배송중') {
        alert('배송중인 상품은 취소할 수 없습니다.');
        return;
    }

    if (!confirm('정말 주문을 취소하시겠습니까?')) return;

    fetch(`/orders/admin/${orderId}`, {
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
