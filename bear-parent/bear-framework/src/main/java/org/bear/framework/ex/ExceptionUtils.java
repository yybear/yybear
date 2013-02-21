package org.bear.framework.ex;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import org.bear.framework.message.NLS;
import org.springframework.core.NestedRuntimeException;
/**
 * .
 * <p/>
 *
 * @author <a href="mailto:ganqing@any123.com">gan qing</a>
 * @version V1.0, 13-2-19
 */
public class ExceptionUtils {
    private ExceptionUtils() {
    }

    public static Throwable getRootCause(Throwable cause) {
        Throwable rootCause = null;
        while (cause != null && cause != rootCause) {
            rootCause = cause;
            cause = cause.getCause();
        }
        return rootCause;
    }

    public static String buildMessage(int code, Object[] args, String defaultMessage, Throwable cause) {
        String message = null;
        if (code != ErrorCode.SERVER_ERROR) {
            message = NLS.getMessage("error." + code, args, null);
        }
        if (defaultMessage != null) {
            message = message == null ? defaultMessage : message + "; " + defaultMessage;
        }
        if (cause != null) {
            StringBuilder sb = new StringBuilder();
            if (message != null) {
                sb.append(message);
            }
            Set<Throwable> visitedExceptions = new HashSet<Throwable>();
            Throwable tmpEx = cause;
            do {
                sb.append("; nested exception is ").append(cause);
                visitedExceptions.add(tmpEx);
                tmpEx = tmpEx.getCause();
            } while (!(tmpEx == null || visitedExceptions.contains(tmpEx) || tmpEx instanceof GlobalException || tmpEx instanceof NestedRuntimeException));
            return sb.toString();
        } else {
            return message;
        }
    }

    public static String buildStackTrace(Throwable cause) {
        StringWriter sw = new StringWriter();
        cause.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
