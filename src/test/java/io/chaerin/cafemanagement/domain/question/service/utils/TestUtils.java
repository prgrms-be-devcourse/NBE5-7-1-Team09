//package io.chaerin.cafemanagement.domain.question.service.utils;
//
//import io.chaerin.cafemanagement.domain.order.dto.OrderItemUpdateRequestDto;
//import io.chaerin.cafemanagement.domain.order.dto.OrderResponseDto;
//import io.chaerin.cafemanagement.domain.order.dto.OrderUpdateRequestDto;
//import io.chaerin.cafemanagement.domain.order.entity.Order;
//import io.chaerin.cafemanagement.domain.order.entity.OrderItem;
//import io.chaerin.cafemanagement.domain.order.repository.OrderRepository;
//import io.chaerin.cafemanagement.domain.order.service.OrderService;
//import io.chaerin.cafemanagement.domain.product.entity.Product;
//import io.chaerin.cafemanagement.domain.product.repository.ProductRepository;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//public class TestUtils {
//    public static void createProduct(ProductRepository productRepository) {
//        productRepository.save(
//                Product.builder()
//                        .name("1번콩")
//                        .price(1000)
//                        .imageUrl("/url1")
//                        .build()
//        );
//        productRepository.save(
//                Product.builder()
//                        .name("2번콩")
//                        .price(2000)
//                        .imageUrl("/url2")
//                        .build()
//        );
//        productRepository.save(
//                Product.builder()
//                        .name("3번콩")
//                        .price(3000)
//                        .imageUrl("/url3")
//                        .build()
//        );
//
//    }
////
////    public static void createOrders(OrderService orderService) {
////
////        OrderDto o1 = OrderDto.builder()
////                .email("test1@test.com")
////                .address("address1")
////                .postCode("12345")
////                .orderItem(
////                        List.of(new OrderItemUpdateRequestDto(1L, 2),
////                                new OrderItemUpdateRequestDto(2L, 1))
////                )
////                .build();
////
////        OrderDto o2 = OrderDto.builder()
////                .email("test1@test.com")
////                .address("address1")
////                .postCode("12345")
////                .orderItem(
////                        List.of(new OrderItemUpdateRequestDto(1L, 3),
////                                new OrderItemUpdateRequestDto(3L, 1))
////                )
////                .build();
////
////        OrderDto o3 = OrderDto.builder()
////                .email("test1@test.com")
////                .address("address1")
////                .postCode("12345")
////                .orderItem(
////                        List.of(new OrderItemUpdateRequestDto(1L, 2),
////                                new OrderItemUpdateRequestDto(2L, 1))
////                )
////                .build();
////
////
////    }
////
////
////    @Setter
////    public static class OrderDto {
////        private String email;
////        private String address;
////        private String postCode;
////        private List<OrderItemUpdateRequestDto> orderItem;
////
////        @Builder
////        public OrderDto(String email, String address, String postCode, List<OrderItemUpdateRequestDto> orderItem) {
////            this.email = email;
////            this.address = address;
////            this.postCode = postCode;
////            this.orderItem = orderItem;
////        }
////    }
////    @AllArgsConstructor
////    public static class OrderItemUpdateRequestDto{
////        private Long productId;
////        private Integer quantity;
////    }
//
//}
