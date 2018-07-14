/*    
    Copyright (C) 2012 http://software-talk.org/ (developer@software-talk.org)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package ia.utils.astar;

/**
 * A Factory which creates new instances of an implementation of the
 * <code>AbstractNode</code> at given coordinates.
 * <p>
 * Must be implemented and given to <code>Map</code> instance on
 * construction.
 *
 * @see AbstractNode
 * @version 1.0
 */
public interface NodeFactory {

    /**
     * creates new instances of an implementation of the
     * <code>AbstractNode</code>.
     * In an implementation, it should return a new node with its position
     * set to the given x and y values.
     *
     * @param x position on the x-axis
     * @param y position on the y-axis
     * @return
     */
    public AbstractNode createNode(int x, int y);

}
