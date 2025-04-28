document.addEventListener('DOMContentLoaded', () =>{
    let total = 0;

    // 모든 .price 요소들을 가져와서 반복
    document.querySelectorAll('.price').forEach(priceEl => {
        const text = priceEl.textContent.trim(); // 상품가격*개수.. ex) 15000원
        const number = text.replace(/[^\d]/g, ''); // 숫자만 남김..
        const price = parseInt(number, 10);

        total += price;
    });

    const totalPrice = document.getElementById('total-price');
    totalPrice.textContent = `총 금액: ${total.toLocaleString()}원`;

})
