package org.bear.framework.validate;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Iterator;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.ValidationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

import org.bear.framework.ex.ErrorCode;
import org.bear.framework.ex.GlobalException;
import org.bear.framework.message.NLS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:gan.qingx@qq.com">gan qing</a>
 * @version V1.0, 2013-3-1
 */
public class ValidateExceptionAdvice {
private static final Logger LOGGER = LoggerFactory.getLogger(ValidateExceptionAdvice.class);
	
	public void afterThrowingValidationException(ValidationException e) throws GlobalException {
		if(e instanceof MethodConstraintViolationException) { // 方法参数验证
			Set<MethodConstraintViolation<?>> mcvs = ((MethodConstraintViolationException)e)
					.getConstraintViolations();
			for (MethodConstraintViolation<?> mcv : mcvs) {
				ConstraintDescriptor<?> constraintDescriptor = mcv.getConstraintDescriptor();
				Annotation annotation = constraintDescriptor.getAnnotation();
				LOGGER.info("method param {}", mcv.toString());
				throw new GlobalException(getErrorCodeByAnnotation(annotation),rebuildMessage(mcv));
			}
		} else if(e instanceof ConstraintViolationException) { // bean 属性验证
			Set<ConstraintViolation<?>> cvs = ((ConstraintViolationException)e)
					.getConstraintViolations();
			for(ConstraintViolation<?> cv : cvs) {
				ConstraintDescriptor<?> constraintDescriptor = cv.getConstraintDescriptor();
				Annotation annotation = constraintDescriptor.getAnnotation();
				LOGGER.info("field validate {}", cv.getPropertyPath());
				throw new GlobalException(getErrorCodeByAnnotation(annotation), buildFieldMessage(cv));
			}
		}
	}
	
	private int getErrorCodeByAnnotation(Annotation annotation){
		Class<? extends Annotation> annotationType = annotation.annotationType();
		if(NotNull.class.equals(annotationType)||NotEmpty.class.equals(annotationType)){
			return ErrorCode.MISS_PARAM;
		}else if (Min.class.equals(annotationType)||(Max.class.equals(annotationType)||Size.class.equals(annotationType))){
			return ErrorCode.RANGE_ERROR;
		}else if (Pattern.class.equals(annotationType)){
			return ErrorCode.PATTERN_NOT_MATCH;
		} else
			return ErrorCode.VALIDATE_ERROR;
	}
	
	private String buildFieldMessage(ConstraintViolation<?> cv) {
		StringBuffer finalMessage = new StringBuffer();
		String field = cv.getPropertyPath().toString();
		finalMessage.append(NLS.getMessage("Field.message", new Object[]{field}));
		finalMessage.append(" ").append(cv.getMessage());
		return finalMessage.toString();
	}
	
	private String rebuildMessage(MethodConstraintViolation<?> mcv){
		StringBuffer finalMessage = new StringBuffer();
		ConstraintDescriptorImpl cd = (ConstraintDescriptorImpl)mcv.getConstraintDescriptor();
		finalMessage.append(getParamName(mcv));
		if(cd.getElementType()==ElementType.FIELD) {
			// 属性验证异常
			Path path = mcv.getPropertyPath();
			Iterator<Node> nodeIterator = path.iterator();
			//忽略第一
			nodeIterator.next();
			while(nodeIterator.hasNext()){
				finalMessage.append("->");
				Node node = nodeIterator.next();
				finalMessage.append(node.toString());
			}
		} 
		
		finalMessage.append(" ").append(mcv.getMessage());
		return finalMessage.toString();
	}
	
	private String getParamName(MethodConstraintViolation<?> mcv) {
		int pindex = mcv.getParameterIndex();
		String pName = null;
		Method cm = mcv.getMethod();
		Class<?> clazz = cm.getDeclaringClass();
		Class<?>[] clazzInterfaces = clazz.getInterfaces();
		for(Class<?> clazzIface : clazzInterfaces) {
			try {
				Method cmIface = clazzIface.getMethod(cm.getName(), cm.getParameterTypes());
				Annotation[][] as = cmIface.getParameterAnnotations();
				Annotation[] paramAnn = as[pindex];
				for(Annotation ann : paramAnn) {
					if(ann.annotationType().getSimpleName().equals("ParamName")) {
						pName = ((ParamName)ann).value();
						break;
					}
				}
				if(StringUtils.hasText(pName))
					break;
			} catch (Exception e) {
			}
		}
		if(!StringUtils.hasText(pName)) {
			pName = String.valueOf(pindex); // 没有只能填写序号
		} 
		return NLS.getMessage("Parammeter.message", new Object[]{pName});
	}
}
