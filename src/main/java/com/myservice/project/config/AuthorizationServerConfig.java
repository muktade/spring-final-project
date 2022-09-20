//package com.myservice.project.config;//package com.myservice.myproject.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
////@Configuration(proxyBeanMethods = false)
//@Configuration
//public class AuthorizationServerConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//
//
////    @Autowired
////    private PasswordEncoder passwordEncoder;
////
////    @Bean
////    @Order(Ordered.HIGHEST_PRECEDENCE)
////    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http)
////        throws Exception{
////        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
////
////        return http.formLogin(Customizer.withDefaults()).build();
////    }
////
////    @Bean
////    public RegisteredClientRepository registeredClientRepository() {
////        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
////                .clientId("api-client")
////                .clientSecret(passwordEncoder.encode("secret"))
////                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
////                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
////                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
////                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
////                .redirectUri("http://127.0.0.1:8082/login/oauth2/code/api-client-oidc")
////                .redirectUri("http://127.0.0.1:8082/authorized")
////                .scope(OidcScopes.OPENID)
////                .scope("api.read")
////                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
////                .build();
////
////
////        return new InMemoryRegisteredClientRepository(registeredClient);
////    }
////
////    @Bean
////    public JWKSource<SecurityContext> jwkSource() {
////        RSAKey rsaKey = generateRsa();
////        JWKSet jwkSet = new JWKSet(rsaKey);
////        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
////    }
////
////    private static RSAKey generateRsa() {
////        KeyPair keyPair = generateRsaKey();
////        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
////        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
////        return new RSAKey.Builder(publicKey)
////                .privateKey(privateKey)
////                .keyID(UUID.randomUUID().toString())
////                .build();
////    }
////
////
////    private static KeyPair generateRsaKey() {
////        KeyPair keyPair;
////        try {
////            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
////            keyPairGenerator.initialize(2048);
////            keyPair = keyPairGenerator.generateKeyPair();
////        } catch (Exception ex) {
////            throw new IllegalStateException(ex);
////        }
////        return keyPair;
////    }
//
//
//
//}
