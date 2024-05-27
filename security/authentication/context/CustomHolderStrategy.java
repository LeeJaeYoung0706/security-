package newws.client_gateway.security.authentication.context;

import java.util.function.Supplier;

public interface CustomHolderStrategy {

    void clearContext();

    SecurityContext getContext();

    default Supplier<SecurityContext> getDeferredContext() {
        return this::getContext;
    }

    void setContext(SecurityContext context);


    default void setDeferredContext(Supplier<SecurityContext> deferredContext) {
        setContext(deferredContext.get());
    }

    SecurityContext createEmptyContext();
}
