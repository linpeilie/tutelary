package cn.easii.tutelary.client.converter;

import cn.easii.tutelary.client.constants.LoggerConstant;
import cn.easii.tutelary.message.command.domain.LoggerAppender;
import cn.easii.tutelary.message.command.domain.LoggerInfo;
import cn.hutool.core.convert.Convert;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoggerInfoConverter {

    LoggerInfoConverter CONVERTER = Mappers.getMapper(LoggerInfoConverter.class);

    default LoggerInfo map2LoggerInfo(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        LoggerInfo loggerInfo = new LoggerInfo();

        if (map.containsKey(LoggerConstant.name)) {
            loggerInfo.setName(object2String(map.get(LoggerConstant.name)));
        }
        if (map.containsKey(LoggerConstant.clazz)) {
            loggerInfo.setClazz(object2String(map.get(LoggerConstant.clazz)));
        }
        if (map.containsKey(LoggerConstant.level)) {
            loggerInfo.setLevel(object2String(map.get(LoggerConstant.level)));
        }
        if (map.containsKey(LoggerConstant.effectiveLevel)) {
            loggerInfo.setEffectiveLevel(object2String(map.get(LoggerConstant.effectiveLevel)));
        }
        if (map.containsKey(LoggerConstant.additivity)) {
            Object additivity = map.get(LoggerConstant.additivity);
            loggerInfo.setAdditivity(Convert.convert(Boolean.class, additivity));
        }
        if (map.containsKey(LoggerConstant.codeSource)) {
            loggerInfo.setCodeSource(object2String(map.get(LoggerConstant.codeSource)));
        }
        if (map.containsKey(LoggerConstant.config)) {
            loggerInfo.setConfig(object2String(map.get(LoggerConstant.config)));
        }
        if (map.containsKey(LoggerConstant.appenders)) {
            loggerInfo.setAppenders(obj2LoggerAppenders(map.get(LoggerConstant.appenders)));
        }
        return loggerInfo;
    };

    default List<LoggerAppender> obj2LoggerAppenders(Object obj) {
        if (obj == null) {
            return null;
        }
        List<Map<String, Object>> map = (List<Map<String, Object>>) obj;
        return map.stream().map(this::map2LoggerAppender).collect(Collectors.toList());
    }

    default LoggerAppender map2LoggerAppender(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        LoggerAppender loggerAppender = new LoggerAppender();
        if (map.containsKey(LoggerConstant.AppendersConstant.name)) {
            loggerAppender.setName(object2String(map.get(LoggerConstant.AppendersConstant.name)));
        }
        if (map.containsKey(LoggerConstant.AppendersConstant.file)) {
            loggerAppender.setFile(object2String(map.get(LoggerConstant.AppendersConstant.file)));
        }
        if (map.containsKey(LoggerConstant.AppendersConstant.blocking)) {
            loggerAppender.setBlocking(object2String(map.get(LoggerConstant.AppendersConstant.blocking)));
        }
        if (map.containsKey(LoggerConstant.AppendersConstant.appenderRef)) {
            loggerAppender.setAppenderRef((List<String>) map.get(LoggerConstant.AppendersConstant.appenderRef));
        }
        if (map.containsKey(LoggerConstant.AppendersConstant.target)) {
            loggerAppender.setTarget(object2String(map.get(LoggerConstant.AppendersConstant.target)));
        }
        return loggerAppender;
    }

    default String object2String(Object obj) {
        return obj == null ? "" : obj.toString();
    }

}
