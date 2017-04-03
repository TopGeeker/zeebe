package org.camunda.tngp.client.event.impl;

import java.time.Instant;
import java.util.Map;

import org.camunda.tngp.client.event.TaskEvent;
import org.camunda.tngp.client.task.impl.MsgPackField;
import org.camunda.tngp.protocol.Protocol;

public class TaskEventImpl implements TaskEvent
{

    protected String eventType;
    protected Map<String, Object> headers;
    protected Long lockTime;
    protected Integer lockOwner;
    protected Integer retries;
    protected String type;
    protected final MsgPackField payload = new MsgPackField();

    @Override
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public Instant getLockExpirationTime()
    {
        if (lockTime != null && !lockTime.equals(Protocol.INSTANT_NULL_VALUE))
        {
            return Instant.ofEpochMilli(lockTime);
        }
        else
        {
            return null;
        }
    }

    public void setLockTime(Long lockTime)
    {
        this.lockTime = lockTime;
    }

    @Override
    public String getEventType()
    {
        return eventType;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    @Override
    public Map<String, Object> getHeaders()
    {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers)
    {
        this.headers = headers;
    }

    @Override
    public Integer getLockOwner()
    {
        return lockOwner;
    }

    public void setLockOwner(Integer lockOwner)
    {
        this.lockOwner = lockOwner;
    }

    @Override
    public String getPayload()
    {
        return payload.getAsJson();
    }

    public void setPayload(byte[] msgPack)
    {
        this.payload.setMsgPack(msgPack);
    }

    public Integer getRetries()
    {
        return retries;
    }

    public void setRetries(Integer retries)
    {
        this.retries = retries;
    }

}
