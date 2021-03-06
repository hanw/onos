/*
 * Copyright 2014 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.net.intent.constraint;

import com.google.common.annotations.Beta;
import org.onosproject.net.Link;
import org.onosproject.net.Path;
import org.onosproject.net.intent.Constraint;
import org.onosproject.net.resource.link.LinkResourceService;

/**
 * Abstract base class for various constraints that evaluate link viability
 * in a yes/no fashion.
 */
@Beta
public abstract class BooleanConstraint implements Constraint {

    /**
     * Returns true if the specified link satisfies the constraint.
     *
     * @param link            link to be validated
     * @param resourceService resource service for checking available link resources
     * @return true if link is viable
     */
    public abstract boolean isValid(Link link, LinkResourceService resourceService);

    /**
     * {@inheritDoc}
     *
     * Negative return value means the specified link does not satisfy this constraint.
     *
     * @param link {@inheritDoc}
     * @param resourceService {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public double cost(Link link, LinkResourceService resourceService) {
        return isValid(link, resourceService) ? +1 : -1;
    }

    @Override
    public boolean validate(Path path, LinkResourceService resourceService) {
        for (Link link : path.links()) {
            if (!isValid(link, resourceService)) {
                return false;
            }
        }
        return true;
    }

}
