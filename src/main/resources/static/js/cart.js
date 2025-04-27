
const cart = {}; // 장바구니 저장소

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.add-to-cart-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const productId = btn.dataset.productId;
            const name = btn.closest('.product-card').querySelector('.product-name').textContent.trim();
            const price = parseInt(btn.dataset.price);
            console.log('가격 확인:', price);
            if (!cart[productId]) {
                cart[productId] = { name, quantity: 0, price };
            }
            cart[productId].quantity += 1;

            renderCart();
            updateOrderItemsInputs();
            updateOrderTotalPrice();
        });
    });

    const form = document.getElementById('orderForm');
    form.addEventListener('submit', (e) => {
        e.preventDefault();
        if (confirm('주문을 완료하시겠습니까?')) {
            alert('주문이 완료되었습니다!');
            form.submit();
        } else {
            alert('주문이 취소되었습니다.');
        }
    });
});

function changeQuantity(productId, delta) {
    if (!cart[productId]) return;
    cart[productId].quantity += delta;
    if (cart[productId].quantity <= 0) {
        delete cart[productId];
    }
    renderCart();
    updateOrderItemsInputs();
    updateOrderTotalPrice();
}

function renderCart() {
    const cartList = document.getElementById('cartList');
    cartList.innerHTML = '';

    Object.entries(cart).forEach(([productId, item]) => {
        const row = document.createElement('div');
        row.className = 'row';
        row.innerHTML = `
            <h5 class="col p-0">${item.name}</h5>
            <h6 class="col d-flex align-items-center justify-content-end">
                <button type="button" onclick="changeQuantity('${productId}', -1)">
                    <img src="./assets/product_minus_icon.png">
                </button>
                <span class="badge bg-dark mx-2 quantity">${item.quantity}개</span>
                <button type="button" onclick="changeQuantity('${productId}', 1)">
                    <img src="./assets/product_add_small_icon.png">
                </button>
            </h6>
        `;
        cartList.appendChild(row);
    });
}

function updateOrderItemsInputs() {
    const container = document.getElementById('orderItemsContainer');
    container.innerHTML = '';
    let index = 0;
    for (const [productId, item] of Object.entries(cart)) {
        const pidInput = document.createElement('input');
        pidInput.type = 'hidden';
        pidInput.name = `orderItem[${index}].productId`;
        pidInput.value = productId;

        const qtyInput = document.createElement('input');
        qtyInput.type = 'hidden';
        qtyInput.name = `orderItem[${index}].quantity`;
        qtyInput.value = item.quantity;

        container.appendChild(pidInput);
        container.appendChild(qtyInput);
        index++;
    }
}

function updateOrderTotalPrice() {
    let total = 0;

    for (const item of Object.values(cart)) {
        total += item.price * item.quantity;
    }

    const priceDisplay = document.getElementById('total-price');
    if (priceDisplay) {
        priceDisplay.textContent = total.toString() + '원';
    }
}