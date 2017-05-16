/*
 * Copyright 2014 The Closure Compiler Authors
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

/**
 * @fileoverview Definitions for Object.observe related APIs. These APIs will
 * most likely be part of ES7 spec.
 * @externs
 */


/**
 * @param {!Object} object
 * @param {!Function} callback
 * TODO(dpapad): Make this type more specific, here and below.
 * @param {!Array<string>=} opt_acceptList
 * @see http://wiki.ecmascript.org/doku.php?id=harmony:observe_public_api
 * @return {!Object}
 */
Object.observe = function(object, callback, opt_acceptList) {};


/**
 * @param {!Object} object
 * @param {!Function} callback
 * @see http://wiki.ecmascript.org/doku.php?id=harmony:observe_public_api
 * @return {!Object}
 */
Object.unobserve = function(object, callback) {};


/**
 * @param {!Array<?>} array
 * @param {!Function} callback
 * @see http://wiki.ecmascript.org/doku.php?id=harmony:observe_public_api
 * @return {!Array<?>}
 */
Array.observe = function(array, callback) {};


/**
 * @param {!Array<?>} array
 * @param {!Function} callback
 * @see http://wiki.ecmascript.org/doku.php?id=harmony:observe_public_api
 * @return {!Array<?>}
 */
Array.unobserve = function(array, callback) {};
