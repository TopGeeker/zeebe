/*
 * Zeebe Broker Core
 * Copyright © 2017 camunda services GmbH (info@camunda.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.zeebe.broker.clustering.management.handler;

import io.zeebe.broker.clustering.management.ClusterManager;
import io.zeebe.broker.system.deployment.handler.CreateWorkflowRequestHandler;
import io.zeebe.clustering.management.*;
import io.zeebe.transport.*;
import org.agrona.DirectBuffer;

public class ClusterManagerFragmentHandler implements ServerMessageHandler, ServerRequestHandler
{
    protected final MessageHeaderDecoder messageHeaderDecoder = new MessageHeaderDecoder();

    private final ClusterManager clusterManager;
    private final CreateWorkflowRequestHandler workflowRequestHandler;

    public ClusterManagerFragmentHandler(final ClusterManager clusterManager, final CreateWorkflowRequestHandler workflowRequestHandler)
    {
        this.clusterManager = clusterManager;
        this.workflowRequestHandler = workflowRequestHandler;
    }

    @Override
    public boolean onRequest(ServerOutput output, RemoteAddress remoteAddress, DirectBuffer buffer, int offset,
            int length, long requestId)
    {
        messageHeaderDecoder.wrap(buffer, offset);

        // TODO verify the protocol version

        final int schemaId = messageHeaderDecoder.schemaId();

        if (InvitationResponseDecoder.SCHEMA_ID == schemaId)
        {
            final int templateId = messageHeaderDecoder.templateId();
            switch (templateId)
            {
                case InvitationRequestEncoder.TEMPLATE_ID:
                {
                    return clusterManager.onInvitationRequest(buffer, offset, length, output, remoteAddress, requestId);
                }
                case CreateWorkflowRequestEncoder.TEMPLATE_ID:
                {
                    return workflowRequestHandler.onCreateWorkflowRequest(buffer, offset, length, remoteAddress, requestId);
                }
                default:
                {
                    // TODO: send error response
                    return true;
                }
            }
        }
        else
        {
            return true;
        }
    }

    @Override
    public boolean onMessage(ServerOutput output, RemoteAddress remoteAddress, DirectBuffer buffer, int offset,
            int length)
    {
        messageHeaderDecoder.wrap(buffer, offset);

        final int schemaId = messageHeaderDecoder.schemaId();

        if (CreatePartitionMessageDecoder.SCHEMA_ID == schemaId)
        {
            final int templateId = messageHeaderDecoder.templateId();
            switch (templateId)
            {
                case CreatePartitionMessageDecoder.TEMPLATE_ID:
                {
                    clusterManager.onCreatePartitionMessage(buffer, offset, length);
                    break;
                }
                default:
                {
                    // ignore
                }
            }
        }
        return true;
    }

}
